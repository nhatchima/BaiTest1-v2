<?php
	include "connect.php";
	$mangspmoinhat = array();
	$query = "SELECT * FROM vatpham ORDER BY ID DESC LIMIT 10";
	$data = mysqli_query($conn,$query);
	while($row = mysqli_fetch_assoc($data)){
		array_push($mangspmoinhat,new Sanphammoi(
			$row['id'],
			$row['idloaivatpham'],
			$row['tenvatpham'],
			$row['giavatpham'],
			$row['anhvatpham']));
	}
	echo json_encode($mangspmoinhat);
	class Sanphammoi{
		function Sanphammoi($id,$idloaivatpham,$tenvatpham,$giavatpham,$anhvatpham){
			$this ->id =$id;
			$this ->idloaivatpham =$idloaivatpham;
			$this ->tenvatpham =$tenvatpham;
			$this ->giavatpham =$giavatpham;
			$this ->anhvatpham =$anhvatpham;
		}
	}
?>