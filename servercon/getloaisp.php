<?php
include "connect.php";
$query="SELECT * FROM loaivatpham";
$data=mysqli_query($conn,$query);
$mangloaisp=array();
while($row=mysqli_fetch_assoc($data)){
	array_push($mangloaisp, new loaisp(
		$row['idloaivatpham'],
	    $row['tenloaivatpham'],
	    $row['hinhloaivatpham']));
}
echo json_encode($mangloaisp);
class loaisp{
	function loaisp($idloaivatpham,$tenloaivatpham,$hinhloaivatpham){
    $this->idloaivatpham=$idloaivatpham;
    $this->tenloaivatpham=$tenloaivatpham;
    $this->hinhloaivatpham=$hinhloaivatpham;
	}
}
?>