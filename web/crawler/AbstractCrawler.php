<?php

require_once 'PHPCrawler/classes/phpcrawler.class.php';

abstract class AbstractCrawler extends PHPCrawler
{
	private $_path = array(
	    'url' => array()
	);

	
	public function __construct($config = array())
	{
		parent::__construct ();

		$this->_run();
	}

	abstract protected function _run ();
}
