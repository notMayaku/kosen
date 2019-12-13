<?php
class Company{
   private $code;
   private $name;
   private $address;
   private $phone;
   private $labors;

   public function __construct($code, $name, $address, $phone, $labors){
      $this->code = $code;
      $this->name = $name;
      $this->address = $address;
      $this->phone = $phone;
      $this->labors = $labors;
   }

   public function getCode(){
      return $this->code;
   }

   public function getName(){
      return $this->name;
   }

   public function getAddress(){
      return $this->address;
   }

   public function getPhone(){
      return $this->phone;
   }

   public function getLabors(){
      return $this->labors;
   }
}
?>