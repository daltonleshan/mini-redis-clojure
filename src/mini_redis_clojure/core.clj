(ns mini-redis-clojure.core
	(:require [ring.adapter.jetty :as jetty]
		  [ring.middleware.json :as middleware]
		  [compojure.core :refer [defroutes GET POST]]
                  [compojure.route :as route] 
                  [compojure.handler :as handler]
		  [com.rpl.specter :as s]
	)
	(use [clojure.string :only (split triml)])
)

(def mmap (atom {}))

(defn processString [op key1 values]
	(println "data received")
	(cond 
		(= op "get") (def toReturn (get (deref mmap) key1))
		(= op "set") (def toReturn (swap! mmap assoc key1 values))
		(= op "delete") (def toReturn (swap! mmap dissoc key1 ))
	)
	(println toReturn)
	toReturn
)

(def mmap2 (atom {}))

(defn processList [op key1 value]
	(println "list data received")
	(cond
		(= op "get") (def toReturn (get (deref mmap2) key1))
		(= op "set") (def toReturn (swap! mmap2 assoc key1 (list value)))
		(= op "delete") (def toReturn (swap! mmap2 dissoc key1 ))
		(= op "append") (def toReturn (swap! mmap2 assoc key1 (conj (get 
					(deref mmap2) key1) value)))
		(= op "pop")	(def toReturn (last (get (deref mmap2) key1)))	
	)
	(println toReturn)
	toReturn
)

(def mmap3 (atom {}))

(defn processMap [op key1 value]
	(println "map data received")
	(cond
		(= op "get") (def toReturn (get (deref mmap3) key1))
		(= op "set") (def toReturn (swap! mmap3 assoc key1 (apply hash-map 
						(.split value " ")))) 
	
	)
	(println toReturn)
	toReturn
)

(defroutes my-handler
	(POST "/string" [ op key1 value :as request] 
		 (processString op key1 value))
	(POST "/list" [op key1 value :as request]
		 (processList op key1 value)) 
	(POST "/map" [op key1 value :as request]
		 (processMap op key1 value))
	(route/not-found "Page not found")
)

(def app 
	(middleware/wrap-json-params my-handler)
)
(defn -main []
	; (foo "Hello, World!")
	(jetty/run-jetty  app {:port 4567}))

