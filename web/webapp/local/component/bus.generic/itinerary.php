<? 
$db = Database::singleton();

$sth = $db->prepare ("SELECT b.bus_name, b.bus_number, i.*, m.marker_name FROM cms.itineraries i 
						INNER JOIN cms.bus b ON b.bus_id = i.bus_id 
						INNER JOIN cms.markers m ON m.marker_id = i.marker_id
						WHERE b.bus_id = ". $itemId . " ORDER BY i.itinerary_id");

$sth->execute();
$count = -1;
?>

<table style="border: 1px solid #000">

<? while($itineraryObj = $sth->fetchObject ()):
	$count++;
	if(!isset($cols)) $cols = $itineraryObj->_order;
?>
	<? if($cols++ > $itineraryObj->_order): unset($cols); $count = 0;?>
		</tr>
		<tr>
	<? endif;?>

	<? if( isset($cols) && $cols >= $count):?>
		<? while ($count++ < $cols - 1):?>
			<td style="border: 1px solid #000">
				&nbsp;
			</td>	
		<? endwhile;?>
	<? endif;?>
	
	<td style="border: 1px solid #000">
		<? echo trim($itineraryObj->schedule)?>
	</td>
<?endwhile;?>

</tr>
</table>