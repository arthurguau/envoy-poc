// import necessary module
import http from "k6/http";

export default function () {

  // define URL and payload
  const url = "http://host.docker.internal:8080/parties/";
  
  const payload = JSON.stringify({
    "name": "Arthur",
    "email": "arthur@hotmail.com",
	"address": "Sydney, NSW"
  });

  const params = {
    headers: {
      "Content-Type": "application/json",
	  "Authorization": "Bearer token1",
	  "Authorisation": "Bearer token2"
    },
  };

  // send a post request and save response as a variable
  const res = http.post(url, payload, params);
}
