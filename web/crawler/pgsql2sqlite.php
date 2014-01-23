<?php

$content = file_get_contents ("dump.sql");


preg_match_all('/CREATE TABLE(.*)\);$/i',$content, $matches);

var_dump($matches);