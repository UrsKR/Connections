(ns collections.Collections_Test
  (:use clojure.test)
  (:use People))

(deftest peopleKnowEachOtherOnceIntroduced
  (def Urs (person "Urs"))
  (def Sandra (person "Sandra"))
  (introduce Urs Sandra)
  (is (true? (knows? Urs Sandra))))

(run-tests)