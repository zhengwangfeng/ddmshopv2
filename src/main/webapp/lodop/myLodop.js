  var LODOP; //声明为全局变量 
 function getPrinterCount() {	
		LODOP=getLodop();  
		return LODOP.GET_PRINTER_COUNT();	
  };
  function getPrinterName(iPrinterNO) {	
		LODOP=getLodop();  
		return LODOP.GET_PRINTER_NAME(iPrinterNO);	
 };