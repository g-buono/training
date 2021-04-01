const searchForm = document.querySelector("#search-form");

searchForm.addEventListener("submit", event => {
    event.preventDefault();

    fetch("/libs/granite/csrf/token.json", {method: "GET"})
	.then(response => response.json())
	.then(data => {
        const params = {
            searchQuery: document.querySelector("#search-query").value,
            pagesLocation: searchForm.getAttribute("data-pages-location"),
            assetsLocation: searchForm.getAttribute("data-assets-location")
        };
		
		const headers = {
            "Content-Type": "application/json",
            "CSRF-Token": data.token,
        }
		
        return fetch("/bin/search?" + new URLSearchParams(Object.entries(params)), {method: "POST", headers: headers});
    })
	.then(response => response.json())
	.then(pagesList => {
        if (!pagesList.length) {
			document.querySelector('#search-results').innerText = searchForm.getAttribute("data-no-results-message");
		}
		else {
			const searchResults = document.querySelector('#search-results');
			
			for (let page of pagesList) {
				const anchor = document.createElement('a');
				const newLine = document.createElement('br');
				anchor.innerText = page.title;
				anchor.setAttribute('href', page.url + '.html');
				searchResults.appendChild(anchor);
				searchResults.appendChild(newLine);
			}
		}
    });
});
