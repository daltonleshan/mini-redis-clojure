(ns mini-redis-clojure.core
	(:require [ring.adapter.jetty :as jetty])
	(:require [compojure.core :refer [defroutes GET POST]]
              [compojure.route :as route]))
(defn handler [req]
	{:status 200 :body "Hello, World!"})

(defn stringOps [op & values]
	(format "Op received:, %s" op))

(defroutes my-handler
	(GET "/string/:op" [op] (format "received:, %s" op))
	(GET "/list/:op" [op] (stringOps op)) 
	(POST "/hello" [] "Hello, World!")

	(route/not-found "Page not found"))

(defn -main []
	; (foo "Hello, World!")
	(jetty/run-jetty my-handler {:port 4567}))
