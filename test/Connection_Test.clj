(ns connections.test
    (:use clojure.test)
    (:use Connections))

(deftest friendlyAttitudeYieldsCooperation
  (is (= "Cooperative" ((connect "Urs" "Hunde" "Friendly") "Urs" "Hunde"))))

(deftest relationshipsAreCommutative
  (is (= "Cooperative" ((connect "Urs" "Hunde" "Friendly") "Hunde" "Urs"))))

(deftest antagonisticAttitudeYieldsLackOfCooperation
  (is (= "Uncooperative" ((connect "Urs" "Stinker" "Antagonistic") "Urs" "Stinker"))))

(deftest ignoranceYieldsNoPrevalentBehaviour
      (is (= "None" ((connect "Urs" "Hunde" "Friendly") "Urs" "Stinker"))))

(deftest strangeConnectionsLeadToUnknownBehaviour
      (is (= "None" ((connect "Urs" "Hunde" "Harky") "Urs" "Hunde"))))

(comment (deftest friendlyBehaviourIsTransitive
      (
          (def firstFriendship (connect "Urs" "Hunde" "Friendly"))
          (def secondFriendship (connect "Hunde" "Hundebesitzer" "Friendly"))
          (is (= "Friendly" (firstFriendship secondFriendship)))
      )))

(run-tests)