<?php
	include "connect.php";
	$page = $_GET['page'];
	$idsp = $_POST['idloaivatpham'];
	$volumn = 5;
	$range = ($page - 1) * $volumn;
	$mangsanpham = array();
	$query = "SELECT * FROM vatpham WHERE idloaivatpham = $idsp LIMIT $range,$volumn";
	$data = mysqli_query($conn, $query);
	while($row = mysqli_fetch_assoc($data)){
		array_push($mangsanpham,new Sanpham(
			$row['id'],
			$row['idloaivatpham'],
			$row['tenvatpham'],
			$row['giavatpham'],
			$row['anhvatpham']));
			
	}
	echo json_encode($mangsanpham);
	class Sanpham{
		function Sanpham($id,$idloaivatpham,$tenvatpham,$giavatpham,$anhvatpham){

			$this->id =$id;
			$this->idloaivatpham =$idloaivatpham;
			$this->tenvatpham =$tenvatpham;
			$this->giavatpham =$giavatpham;
			$this->anhvatpham =$anhvatpham;

		}
	}
?>