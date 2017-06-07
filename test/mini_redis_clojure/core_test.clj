(ns mini-redis-clojure.core-test
  (:require [clojure.test :refer :all]
            [mini-redis-clojure.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest mapOps
  (testing "Test all ops on map." 
    (is (= {"a" {"b" "c"}} (processMap "set" "a" "b c") ))
    (is (= {"b" "c"} (processMap "get" "a" "")))
  )
)

(deftest stringOps
  (testing "Test on all string ops."
    (is (= { "a" "b c"} (processString "set" "a" "b c")))
    (is (= "b c" (processString "get" "a" "")))
    (is (= {} (processString "delete" "a" "")))
  )
)

(deftest listOps
  (testing "Test on all list ops."
    (is (= {"a" (list "b")} (processList "set" "a" "b")))
    (is (= {"a" (list "c" "b" )} (processList "append" "a" "c")))
    (is (= "b" (processList "pop" "a" "")))
    (is (= {} (processList "delete" "a" "")))
  )
)
