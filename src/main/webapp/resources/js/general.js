function selectFileName() {
	let input = document.getElementById('media-form:upload');
	let span = document.getElementById('mediaFileName');
	
	input.addEventListener('change', showFileName);
	
	function showFileName(event) {
		let input = event.srcElement;
		span.textContent = input.files[0].name;
	}
}

function fallbackCopyToClipboard(url, message) {
	  let txtArea = document.createElement("textarea");
	  txtArea.value = url;
	  
	  txtArea.style.top = "0";
	  txtArea.style.left = "0";
	  txtArea.style.position = "fixed";
	  
	  document.body.appendChild(txtArea);
	  txtArea.focus();
	  txtArea.select();

	  try {
	    let successful = document.execCommand('copy');
	    let msg = successful ? 'successful' : 'unsuccessful';
	    console.log('Fallback: Copying text command was ' + msg);
	    copyGrowl.renderMessage({"summary": message,
            					 "severity":"info"});
	  } catch (err) {
	    console.error('Fallback: Oops, unable to copy', err);
	  }

	  document.body.removeChild(txtArea);
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
	form.event.preventDefault();
	document.getElementById(form).reset();
}

function setReplyCommentId(value) {
	let id = value;
	document.getElementById("commentNumber").innerHTML = id;
	document.getElementById("reply_comment_id").value = id;
}

function setCommentPostId(value) {
	let id = value;
	document.getElementById("postNumber").innerHTML = id;
	document.getElementById("comment_post_id").value = id;
}

function getActualYear() {
	let year = new Date().getFullYear();
	document.write(year);
}

function enableActualPage() {
	let header = document.getElementById("topBar");
	let btns = header.getElementsByClassName("w3-bar-item");
	for (const element of btns) {
		element.addEventListener("click", function() {
			var current = document.getElementsByClassName("w3-grey");
			current[0].className = current[0].className.replace(" w3-grey", "")
			this.className += " w3-grey";
		});
	}
}
