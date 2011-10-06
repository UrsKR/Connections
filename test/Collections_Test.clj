(ns collections.Collections_Test
    (:use clojure.test)
    (:use Collections))

(deftest emptyListContainsNothing
  (is (not (list-contains? '() 1))))

(deftest singleElementListContainsSingleElement
  (is (list-contains? '(1) 1)))

(deftest singleElementListContainsNoDifferentElement
  (is (not (list-contains? '(1) 2))))

(deftest multiElementListContainsSingleElement
  (is (list-contains? '(1 2) 1)))

(deftest multiElementListContainsAllElements
  (is (list-contains? '(1 2) 1 2)))

(deftest multiElementListDoesNotContainDifferentElements
  (is (not (list-contains? '(1 2) 3 2))))

(deftest multiElementListDoesNotContainDifferentElementsRegardlessOfOrder
  (is (not (list-contains? '(1 2) 2 3))))

(run-tests)