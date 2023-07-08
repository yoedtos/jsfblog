function selectFileName() {
	var input = document.getElementById('j_idt57:upload');
	var fileInfo = document.getElementById('uploadFileName');
	
	input.addEventListener('change', showFileName);
	
	function showFileName(event) {
		var input = event.srcElement;
		var fileName = input.files[0].name;
		fileInfo.textContent = fileName;
	}
}

function fallbackCopyToClipboard(url, message) {
	  var text = document.createElement("textarea");
	  text.value = url;
	  
	  text.style.top = "0";
	  text.style.left = "0";
	  text.style.position = "fixed";
	  
	  document.body.appendChild(text);
	  text.focus();
	  text.select();

	  try {
	    var successful = document.execCommand('copy');
	    var msg = successful ? 'successful' : 'unsuccessful';
	    console.log('Fallback: Copying text command was ' + msg);
	    copyGrowl.renderMessage({"summary": message,
            					 "severity":"info"});
	  } catch (err) {
	    console.error('Fallback: Oops, unable to copy', err);
	  }

	  document.body.removeChild(text);
	}

function copyToClipboard(url, message) {
	//The browser may not be support this API
	// call fallbackCopyToClipboard(value)
	if (!navigator.clipboard) {
		fallbackCopyToClipboard(url, message);
		return;
	}

	navigator.clipboard.writeText(url).then(function() {
		console.log('Async: ' + message);
		copyGrowl.renderMessage({"summary": message,
			 					 "severity":"info"});
	}, function(err) {
		console.error('Async: Could not copy text: ', err);
	});
}

function cancelDialog(event) {
	PF('comDlg').hide();
	event.preventDefault();
}

function cancelReply(event) {
	PF('replyDlg').hide();
	event.preventDefault();
}

function formReset(form) {
	event.preventDefault();
	document.getElementById(form).reset();
}

function setReplyCommentId(value) {
	var id = value;
	document.getElementById("commentNumber").innerHTML = id;
	document.getElementById("reply_comment_id").value = id;
}

function setCommentPostId(value) {
	var id = value;
	document.getElementById("postNumber").innerHTML = id;
	document.getElementById("comment_post_id").value = id;
}

function getActualYear() {
	var year = new Date().getFullYear();
	document.write(year);
}

function enableActualPage() {
	var header = document.getElementById("topBar");
	var btns = header.getElementsByClassName("w3-bar-item");
	for (var i = 0; i < btns.length; i++) {
		btns[i].addEventListener("click", function() {
			var current = document.getElementsByClassName("w3-grey");
			current[0].className = current[0].className.replace(" w3-grey", "")
			this.className += " w3-grey";
		});
	}
}
