(ns mini-redis-clojure.maps)

(def my-map { })

(defn stringOps [op & value]
	;if else statements to switch between the ops
	(if (= op "get")
		;get data from map
		)
	(if (= op "set")
		;create/overwrite
		
		)
	(if (= op "delete")
		;delete from map
		)
	)