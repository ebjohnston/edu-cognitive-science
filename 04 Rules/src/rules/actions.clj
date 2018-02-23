;; Rule-Based Reasoning
;; Rule / Fact Modifiers
;; ========================================
;; Ethan Johnston
;; CS 390L - Cognitive Science and AI
;; Dr. Jody Paul
;; 2 December 2017
;; ========================================
;; Collaborated with:
;;   -Harley Dutton
;;   -Heather DeMarco
;;   -Joseph Brennan
;; ========================================
;; Partially written by: Jody Paul
(ns rules.actions
  (:require [rules.utils :refer :all]
            [clojure.walk :refer :all]
            [clojure.pprint :refer :all]))

;; MEMORY STRUCTURES
;; Rule base
(def ^:dynamic *rule-base* '(RULE-BASE))
;; Working memory
(def ^:dynamic *working-memory* '(WORKING-MEMORY))

(defn clear-rule-base!
  "Resets global memory of rules"
  []
  (def ^:dynamic *rule-base* '(RULE-BASE)))

(defn clear-working-memory!
  "Resets working memory"
  []
  (def ^:dynamic *working-memory* '(WORKING-MEMORY)))

(defn add-rule!
  "Adds rule to the rule base."
  [rule]
  (def ^:dynamic *rule-base* (concat *rule-base* (list rule))))

(defn retract-rule!
  "Retracts a rule from the rule base.
  Does nothing if rule was not in rule base."
  [rule]
  (if (some #(= rule %) (body *rule-base*))
    (def ^:dynamic *rule-base* (remove #(= rule %) *rule-base*))))

(defn add-fact!
  "Adds fact to working memory.
  Does not create duplicate entries."
  [fact]
  (if (not-any? #(= fact %) (body *working-memory*))
    (def ^:dynamic *working-memory* (concat *working-memory* (list fact)))))

(defn retract-fact!
  "Retracts a fact from working memory.
  Does nothing if fact was not in working memory."
  [fact]
  (if (some #(= fact %) (body *working-memory*))
    (def ^:dynamic *working-memory* (remove #(= fact %) *working-memory*))))

(defn rule-condition-satisfied?
  "Evaluates rule condition"
  [rule]
  (let [condition (role-filler 'CONDITION rule)]
    (eval condition)))

(defn any-activatable-rule?
  "Determines if any rule from the rule base can be activated"
  []
  (some #(true? (eval (role-filler 'CONDITION %))) (body *rule-base*)))

(defn activatable-rule
  "Returns rule if it is activatable; otherwise returns nil"
  [rule]
  (if (rule-condition-satisfied? rule)
    rule
    nil))

(defn all-activatable-rules
  "Collects all rules from the rule base that can be activated"
  []
  (let [acts (walk activatable-rule concat (body *rule-base*))]
    (remove nil? acts)))

(defn activate-any-rule
  "Finds any rule tha can be activated and activate it"
  []
  (if (any-activatable-rule?)
    (eval (role-filler 'ACTION (first (all-activatable-rules))))))

(defn activate-rule
  "Finds given rule and activates it if present"
  [rule]
  (if (some #(= rule %) (body *rule-base*))
    (eval (role-filler 'ACTION (activatable-rule rule)))))
