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
		

		parent::__construct ();
	}
	protected function _run()
	{
		$this->_setupCategories ();
		$this->_setupCities ();

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

		
		$fp = fopen(dirname(__FILE__).'/log.txt', 'w');

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

		for($i = 300; $i < 316; $i++)
		{
			$this->setURL($this->_base . str_pad($i, 3, "0", STR_PAD_LEFT).'.html');
			// Thats enough, now here we go
			$this->go();

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
		$sentido = 1;
		$this->_status = self::BUSNAME;

		if($dom->find('td.tittab_maior',0))
		{
			echo $dom->find('td.tittab_maior',0)->plaintext.$lb;
		}
		else if($dom->find('td.fontlinha',0))
		{
			echo $dom->find('td.fontlinha',0)->plaintext.$lb;
		}
		
		foreach ($dom->find('table') as $key => $table) 
		{
			
			if( $table->find('.tittab_maior') && preg_match('/Sentido:/', $table->plaintext) )
			{
				echo "##### Starting itinerary...#####".$lb;
				$this->_status = self::ITINERARY;
			}
			else if( $table->find('tr td table tr td.fontstatususuazul') && preg_match('/Sentido:/', $table->plaintext) )
			{
				echo "##### Starting itinerary...#####".$lb;
				$this->_status = self::ITINERARY;
			}

			//capturando o itinerário 
			// cada td da tabela de itinerário possui o nome de uma rua
			if( !$table->find('tr td.tittab_maior') && !$table->find('table tr td.fontstatususuazul') && preg_match('/Sentido:/', $table->plaintext) )
			{
				
				if($sentido++ % 2)
					echo "Linha: ". ($line++).$lb;

				foreach ($table->find('td') as $key => $value) {
					if($key)
					{
						$street = explode('-', $value->plaintext);
						echo trim($street[1]).$lb;
					}
					else
					{
						echo $value->plaintext.$lb;
					}
				}
				
			}

			if($this->_status == self::TIME && !preg_match('/Plano Funcional:/', $table->plaintext))
			{
				require_once 'htmlpurifier/library/HTMLPurifier.auto.php';

				$config = HTMLPurifier_Config::createDefault();
				$purifier = new HTMLPurifier($config);
				$config->set('Core.Encoding', 'ISO-8859-1');

				$table2 =  str_get_html($purifier->purify($table));

				foreach ($table2->find('tr') as $keyTr => $trs) 
				{
					foreach ($trs->find('td') as $keyTd => $tds) 
					{
						echo '['. trim($tds) .']';
					}

					echo $lb;
				}
			}

			if(preg_match('/Plano Funcional:/', $table->plaintext) )
			{
				$this->_status = self::TIME;

				echo "##### Finishing itinerary...#####".$lb.$lb;
				echo $table->find ('.fontstatususuazul', 0)->plaintext.$lb;
				echo "##### Starting time crawler...#####".$lb;
			}
		}
		return 0;
	
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
	private function _setupCities ()
	{
		$this->_cities ['sp'] = 'sao-paulo';
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