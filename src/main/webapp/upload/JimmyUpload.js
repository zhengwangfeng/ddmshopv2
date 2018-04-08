
/**
 * jimmy
 * @param title
 * @param fileName
 */
function selectP(title,fileName){
	 layer.open({
		  type: 2,
		  title: title,
		  closeBtn: 1,
		  fix: true,
		  shadeClose: true,
		  shade: 0.8,
		  area: ['500px', '400px'],
		  content: 'uploadController.do?selectimages&fileName='+fileName //iframe的url
		});  
}