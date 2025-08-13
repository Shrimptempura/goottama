function previewImg(event) {
		const input = event.target;
		const reader = new FileReader();
		
		reader.onload = function(){
			const preview = document.getElementById('preview');
			preview.src = reader.result;
			preview.style.display = 'block';
		
		document.querySelector('.preview-wrapper').style.display = 'block';
		  };
		
		if(input.files && input.files[0]){
			reader.readAsDataURL(input.files[0]);
		}
	}