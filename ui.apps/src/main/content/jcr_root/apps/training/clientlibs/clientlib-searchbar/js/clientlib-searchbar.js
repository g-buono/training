let searchForm = document.querySelector('#search-form');

searchForm.addEventListener('submit', (event) => {
	event.preventDefault();
			
	fetch('/libs/granite/csrf/token.json', 
		 {
			method: 'GET',
		 })
		 .then(response => response.json())
		 .then(data => {
			const params = {
			    searchQuery: document.querySelector('#search-query').value,
			    pagesLocation: searchForm.getAttribute('data-pages-location'),
			    assetsLocation: searchForm.getAttribute('data-assets-location')
			};
	
			const urlParams = new URLSearchParams(Object.entries(params));
			
		 	fetch('/bin/search?' + urlParams, 
				 {
					method: 'POST',
				    headers: {
				        'Content-Type': 'application/json',
						'CSRF-Token': data.token
				    }
				 })
				 .then(response => response.json())
				 .then(data => {
				 	console.log('Success:', data);
				 })
				 .catch(error => {
				 	console.error('Error:', error);
				 });
		 })
		 .catch(error => {
		 	console.error('Error:', error);
		 });
});