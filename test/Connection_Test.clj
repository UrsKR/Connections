(ns connections.test
    (:use clojure.test)
    (:use Connections))

(deftest friendsCooperate
    (is (= "Cooperative" ((befriend "Urs" "Hunde") "Urs" "Hunde"))))

(deftest enemiesHinder
      (is (= "Uncooperative" ((oppose "Urs" "Stinker") "Urs" "Stinker"))))

(deftest ignoranceYieldsNoPrevalentBehaviour
        (is (= "None" ((befriend "Urs" "Hunde") "Urs" "Stinker"))))

(deftest relationshipsAreCommutative
  (is (= "Cooperative" ((befriend "Urs" "Hunde" ) "Hunde" "Urs"))))

(comment (deftest friendlyBehaviourIsTransitive
      (
          (def firstFriendship (connect "Urs" "Hunde" "Friendly"))
          (def secondFriendship (connect "Hunde" "Hundebesitzer" "Friendly"))
          (is (= "Friendly" (firstFriendship secondFriendship)))
      )))

(run-tests)