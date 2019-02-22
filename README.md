# Movingimage trial demo

## Video probing service

* probe endpoint -> creates/updates info about video
* long running operation -> use asynchronous API
* using REST endpoint to push request into MQ
* actual probe operation is asynchronous, consuming MQ

### probe endpoint POST /probe { url: String }
* push probe url as new requested operation into MQ
* return immediately

* Can be tested with
		curl localhost:8080/probe -XPOST -H "Content-Type: application/json" -d '{ "url": "http://localhost/videos/5.mp4"}'


### probe worker
* receive probe operations from MQ
* download video, store locally in temp file
* probe with ffmpeg
* saves results into video info service
* cleanup temp file

## Video information service

* CRUD information about video
* REST endpoints

### POST /infos { name,format,duration }
* called by probe worker to add video information

### GET /infos?name={name}

* example

		curl "http://localhost:8081/infos?name=2.mp4"

