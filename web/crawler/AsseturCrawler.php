<?php

require_once 'AbstractCrawler.php';
require_once 'simple_html_dom.php';

set_time_limit(0);

class AsseturCrawler extends AbstractCrawler
{
	const BUSNAME = 1;

	const ITINERARY = 2;

	const TIME = 3;

	private $_cities = array (); 

	private $_categories = array ();

	private $_urls = array();

	private $_base = 'http://localhost/busaoapp/web/data/';

	private $_status = self::BUSNAME;

	private $_retry = FALSE;

	private $_place;

	private $_current_city;

	private $_404 = false;

	private $db;

	private $_fields = array(	'name' 			=> '',
								'address' 		=> '',
								'city' 			=> NULL,
								'state' 		=> '',
								'longitude' 	=> '' ,
								'_user' 		=> 1,
								'_create' 		=> 'now()',
								'latitude' 		=> '',
								'phone' 		=> '',
								'site' 			=> '',
								'facebook' 		=> '',
								'twitter' 		=> '',
								'description' 	=> '',
								'price_range' 	=> '',
								'price_value' 	=> '',
								'price_detail' 	=> '',
								'place_category' => '',
								'business_hours' => '',
								'payment_type' 	=> '',
								'details' 		=> '',
								'thumb'			=> NULL,
								'hash' 			=> '');

	public function __construct ()
	{
		$this->db = new PDO ('pgsql:host=localhost port=5432 dbname=busaoapp user=mais password=123', 'mais', '123');
	
		$this->db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		$this->db->exec ('SET datestyle TO ISO, DMY');
		
		$this->db->exec ('SET search_path = cms,titan');

		parent::__construct ();
	}
	protected function _run()
	{
		
		// Only receive content of files with content-type "text/html"
		$this->addContentTypeReceiveRule("#text/html#");

		// Ignore links to pictures, dont even request pictures
		$this->addURLFilterRule("#\.(jpg|jpeg|gif|png)$# i");

		$this->addURLFollowRule("#xxx\/.*># i"); 

		//$this->setLinkExtractionTags(array("href", "src"));

		// Store and send cookie-data like a browser does
		$this->enableCookieHandling(true);
		$this->setConnectionTimeout (200);
		// Set the traffic-limit to 1 MB (in bytes,
		// for testing we dont want to "suck" the whole site)
		$this->setTrafficLimit(1000 * 1024);
		$this->_accessItems ();
	}

	public function handleDocumentInfo(PHPCrawlerDocumentInfo $DocInfo) 
	{
		// Just detect linebreak for output ("\n" in CLI-mode, otherwise "<br>").
		if (PHP_SAPI == "cli") 
			$lb = "\n";
		else 
			$lb = "<br />";

		// Print the URL and the HTTP-status-Code
		//echo "Page requested: ".$DocInfo->url." (".$DocInfo->http_status_code.")".$lb;

		// Print the refering URL
	//	echo "Referer-page: ".$DocInfo->referer_url.$lb;

		// Print if the content of the document was be recieved or not
		//if ($DocInfo->received == true)
		//	echo "Content received: ".$DocInfo->bytes_received." bytes".$lb;
		//else
		//	echo "Content not received".$lb; 

		
		$fp = fopen(dirname(__FILE__).'/log.txt', 'w+');

		fwrite($fp, "Page requested: ".$DocInfo->url." (".$DocInfo->http_status_code.")".time()."\n");

		

		if(!$DocInfo->received)
		{
			$this->_retry = TRUE;
			return;
		}
		
		$this->_retry = false;

		//cidade não cadastrada
		if($DocInfo->http_status_code == 404)
		{
			$this->_404 =  TRUE;
			self::toLog($fp, "PAGINA NAO ENCONTRADA\n");
			
			return;
		}
		
		
			
		$this->_extractInfo ($DocInfo->url, $DocInfo->content);
						

		flush();
	} 

	private function _accessItems ()
	{
		$this->_retry = false;

		if (PHP_SAPI == "cli") 
			$lb = "\n";
		else 
			$lb = "<br />";

		for($i = 1; $i < 1000; $i++)
		{
			$this->setURL($this->_base . str_pad($i, 3, "0", STR_PAD_LEFT).'.html');
			// Thats enough, now here we go
			$this->go();
			//echo $this->_base . str_pad($i, 3, "0", STR_PAD_LEFT).'.html'.$lb;
			// At the end, after the process is finished, we print a short
			// report (see method getProcessReport() for more information)
			$report = $this->getProcessReport();


			/*echo "Summary:".$lb;
			echo "Links followed: ".$report->links_followed.$lb;
			echo "Documents received: ".$report->files_received.$lb;
			echo "Bytes received: ".$report->bytes_received." bytes".$lb;
			echo "Process runtime: ".$report->process_runtime." sec".$lb;
			*/

			flush ();
		}

		unset($this->_urls);

		$this->_urls = array ();

	}

	private function _findPages ()
	{
		
	}
	/**
	* Extract information from POI (Point Of Interest)
	*/
	private function _extractInfo ($url, $html)
	{
		if(strlen($html) < 10)
			return 0;
		
		if (PHP_SAPI == "cli") 
			$lb = "\n";
		else 
			$lb = "<br />";

		$dom = str_get_html($html);
		$line = 'A';
		$sentido = 0;
		$company = '';
		$color = 'N/A';
		$functionalPlanId = 0;
		$this->_status = self::BUSNAME;
		$route = array ('route_way' => '', 'street_id' => 0, 'order' => 1, 'street_name' => '', 'line_id' => 'A', 'bus_id' => 0);

		preg_match_all('/data\/(.*)\.html/', $url, $matches);

		$busNumber = intval($matches[1][0]);

		if($dom->find('td.tittab_maior',0))
		{
			//echo $dom->find('td.tittab_maior',0)->plaintext.$lb;

			if(preg_match('/azul/', $dom->find('td.tittab_maior',0)->firstChild()->getAttribute('src')))
				$color = 'AZUL' ;
			else if(preg_match('/vermelho/', $dom->find('td.tittab_maior',0)->firstChild()->getAttribute('src')))
				$color ='VERMELHO';
			else if(preg_match('/amarelo/', $dom->find('td.tittab_maior',0)->firstChild()->getAttribute('src')))
				$color = 'AMARELO';
			else if(preg_match('/micro/', $dom->find('td.tittab_maior',0)->firstChild()->getAttribute('src')))
				$color = 'MICRO';

			preg_match_all('/EMPRESA:(.*)/', $dom->find('td.tittab_maior',0)->plaintext, $matches);

			$company = trim(str_replace('-', '', $matches [1][0]));

			preg_match_all('/LINHA:(.*)LINHA/', $dom->find('td.tittab_maior',0)->plaintext, $matches);

			$busName = trim(str_replace('-', '', $matches [1][0]));
			
			
			

		}
		else if($dom->find('td.fontlinha',0))
		{
			$busName = $dom->find('td.fontlinha',0)->plaintext.$lb;
		}


		if(empty($busName)) return;
		
		echo $busName.$lb;

		$sth = $this->db->prepare ("SELECT bus_id FROM cms.bus WHERE _no_accents(bus_name) ilike _no_accents('%".pg_escape_string(utf8_encode($busName))."%') AND bus_number = ".$busNumber);
		$sth->execute ();
		$obj = $sth->fetchObject();
		
		if(!$obj)
		{
			$this->db->exec ("INSERT INTO cms.bus  (bus_name, bus_number, color, company) VALUES ('".pg_escape_string(utf8_encode($busName))."', '".$busNumber."', '".$color."', '".utf8_encode($company)."')");
			$sth = $this->db->prepare ("SELECT max(bus_id) as last_id FROM cms.bus");
			$sth->execute ();
			$obj = $sth->fetchObject ();
			$route['bus_id'] = $obj->last_id;
		}
		else
		{
			$route['bus_id'] = $obj->bus_id;
		}

		foreach ($dom->find('table') as $key => $table) 
		{
			
			

			if( $table->find('.tittab_maior') && preg_match('/Sentido:/', $table->plaintext) )
				$this->_status = self::ITINERARY;
			else if( $table->find('tr td table tr td.fontstatususuazul') && preg_match('/Sentido:/', $table->plaintext) )
				$this->_status = self::ITINERARY;
			

			//capturando o itinerário 
			// cada td da tabela de itinerário possui o nome de uma rua
			if( !$table->find('tr td.tittab_maior') && !$table->find('table tr td.fontstatususuazul') && preg_match('/Sentido:/', $table->plaintext) )
			{
				
				if($sentido++ && $sentido % 2)
					$line++;

				$route ['line_id'] = $line;

				//$this->db->exec ("DELETE FROM cms.routes WHERE  bus_id = ".$route['bus_id']." AND line ilike '".$route['line_id']."'");

					
				echo $route ['line_id'];

				foreach ($table->find('td') as $key => $value) 
				{
					if($key)
					{
						$street = explode('-', $value->plaintext);
						$route['street_name'] =  $street[1];
						$route['street_id'] = $this->insertOrUpdateStreets ($route['street_name']);
						$route['order'] = intval($street[0]);
						
						$this->insertOrUpdateRoutes ($route);
					}
					else
						$route['route_way']  = strip_tags($value->plaintext);

				}
			}

			if($this->_status == self::TIME && !preg_match('/Plano Funcional:/', $table->plaintext))
			{
				require_once 'htmlpurifier/library/HTMLPurifier.auto.php';

				$config = HTMLPurifier_Config::createDefault();
				$purifier = new HTMLPurifier($config);
				$config->set('Core.Encoding', 'ISO-8859-1');

				$table2 =  str_get_html($purifier->purify($table));
				
				if(!$table2)
					return ;

				$itinerary = array ( 'bus_id' 	=> $route['bus_id'],
									  'line'	=> $line,
									  'marker_id' => 0,
									  'schedule'	=> 0,
									  'functional_plan_id' => $functionalPlanId
								);

				$markers = array();
				
				$this->db->exec("DELETE FROM cms.itineraries WHERE bus_id = ".$itinerary['bus_id']." AND functional_plan_id = ". $functionalPlanId);

				foreach ($table2->find('tr') as $keyTr => $trs) 
				{
					
					if(!$keyTr)
					{
						$markers = $this->insertMarkers ($trs->find('td'));
						continue;
					}
						

					

					$arrayTds = $trs->find('td');

					$itinerary['line'] = trim(strip_tags($arrayTds [count($arrayTds) - 1]));

					foreach ($arrayTds as $keyTd => $tds) 
					{

						if(!isset($markers[$keyTd])) continue;

						$itinerary ['marker_id'] = $markers[$keyTd]['idbd'];
						$content = trim(strip_tags($tds));
						
						if(empty($content)) continue;

						if(count($trs->find('td')) == $keyTd + 1) continue;

						$itinerary['schedule'] = substr(str_replace(':', '', $content), 0, 2). ":".substr(str_replace(':', '', $content), 2, 2);

						$itinerary ['order'] =  $keyTd;

						$this->insertItinerary ($itinerary);
						
					}
				}

				unset($functionalPlanId);
			}

			if(preg_match('/Plano Funcional:/', $table->plaintext) )
			{
				$this->_status = self::TIME;
				
				$sth = $this->db->prepare ("SELECT functional_plan_id FROM cms.functional_plan WHERE _no_accents(functional_plan_name) ilike _no_accents('%".trim(utf8_encode(strip_tags($table->find ('.fontstatususuazul', 0)->plaintext)))."%')");
				$sth->execute ();
				$obj = $sth->fetchObject();
				if(!$obj)
				{
					$this->db->exec ("INSERT INTO cms.functional_plan  (functional_plan_name) VALUES ('". trim(utf8_encode(strip_tags($table->find ('.fontstatususuazul', 0)->plaintext))). "')");
					
					$sth = $this->db->prepare ("SELECT max(functional_plan_id) as last_id FROM cms.functional_plan");
					$sth->execute ();
					$obj = $sth->fetchObject ();

					$functionalPlanId = $obj->last_id;
				}
				else
					$functionalPlanId = $obj->functional_plan_id;
			}
		}
		
	
		try
		{
			
			
		}
		catch (Exception $e)
		{
			self::toLog ("ERROR: (".$e->getMessage().")\n");
		}
	}

	private function _setupCategories ()
	{
		$this->_categories [] = '';
	}
	private function insertOrUpdateStreets ($streetName)
	{
			$sth = $this->db->prepare ("SELECT street_id FROM cms.streets WHERE _no_accents(street_name) ilike _no_accents('%".pg_escape_string(utf8_encode($streetName))."%')");
			$sth->execute ();
			$obj = $sth->fetchObject();

			if(!$obj)
			{

				$this->db->exec ("INSERT INTO cms.streets  (street_name) VALUES ('".pg_escape_string(utf8_encode($streetName))."')");
				$sth = $this->db->prepare ("SELECT max(street_id) as last_id FROM cms.streets");
				$sth->execute ();
				$obj = $sth->fetchObject ();
				
				return $obj->last_id; 
			}

			return $obj->street_id;
	}

	private function insertOrUpdateRoutes ($route)
	{
		$sth = $this->db->prepare ("SELECT count(*) as total FROM cms.routes WHERE bus_id = ".$route['bus_id']." 
																			   AND street_id  = ".$route['street_id']. "
																			   AND _no_accents(route_way) ilike _no_accents ('".$route['route_way']."') 
																			   AND line ilike '".$route['line_id']."'");
		
		
		$sth->execute ();
		$obj = $sth->fetchObject();

		if(!$obj->total)
		{
			$this->db->exec ("INSERT INTO cms.routes  (bus_id, street_id, line, _order, route_way) 
							 VALUES (".$route['bus_id'].", ".$route['street_id'].", '".$route['line_id']."',".$route['order']." ,'".$route['route_way']."')"); 
		}
	}

	private function insertMarkers ($markers)
	{
		$markersInfo = array ('idbd' => 0, 'label' => '');
		$markersDB = array ();
		foreach ($markers as $key => $value) 
		{
			$value = trim(strip_tags(utf8_encode($value)));
			$markersInfo['label'] = $value;
			$sth = $this->db->prepare ("SELECT marker_id FROM cms.markers WHERE _no_accents(marker_name) ilike _no_accents('%". $value. "%')");
			$sth->execute ();
			
			
			$obj = $sth->fetchObject();

			if($obj)
				$markersInfo ['idbd'] = $obj->marker_id;
			else
			{
				$this->db->exec ("INSERT INTO cms.markers  (marker_name) VALUES ('".$value."')"); 
				
				$sth = $this->db->prepare ("SELECT max(marker_id) as last_id FROM cms.markers");
				$sth->execute ();
				$obj = $sth->fetchObject ();
				
				$markersInfo ['idbd'] = $obj->last_id; 



			}

			$markersDB[$key] = $markersInfo;
		}

		return $markersDB;
	}

	private function insertItinerary ($itinerary)
	{
		$this->db->exec ("INSERT INTO cms.itineraries (bus_id, line, marker_id, schedule, functional_plan_id, _order) VALUES (".$itinerary['bus_id'].", '".$itinerary['line']."', ".$itinerary['marker_id'].", '".$itinerary['schedule']."', ".$itinerary['functional_plan_id'].", ".$itinerary['order'].")");
	}

	private function generateUrl($input)
	{
		$input = mb_convert_case($input, MB_CASE_LOWER, "UTF-8"); 
	    $input = preg_replace("/[^a-zA-Z0-9]+/", "-", $input); 
	    $input = preg_replace("/(-){2,}/", "$1", $input); 
	    $input = trim($input, "-");
	    return $input;
	}

	private function _downloadThumbImage ($imagePath, $POID, $POIName, $fileId = NULL)
	{
		
		try
		{
			
		}
		catch (Exception $e)
		{
			self::toLog ($e->getMessage());
		}

		return $fileId;
	}

	public static function toLog ($text)
	{
		$fp = fopen(dirname(__FILE__).'/log.txt', 'w');
		fwrite($fp, $text."\n");
		fclose($fp);
	}
}