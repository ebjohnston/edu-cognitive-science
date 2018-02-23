;; Rule-Based Reasoning
;; Miscellaneous Utilities
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
;; Originally written by: Jody Paul

(ns rules.utils
  (:require [clojure.pprint :refer :all]))

;; VARIABLES
(defn variable
  "Returns a variable with the given name"
  [x]
  (symbol (str "?" (name x))))

(defn is-var?
  "Determines if parameter is a variable"
  [x]
  (and (symbol? x)
       (re-matches #"^\?.*" (name x))))

(defn name-var
  "Returns the name of a variable"
  [x]
  {:pre [(is-var? x)]}
  (symbol (subs (name x) 1)))


;; FRAMES
(defn header
  "Returns the frame header."
  [frame]
  (first frame))

(defn body
  "Returns the frame body."
  [frame]
  (rest frame))


;; ROLES
(defn roles
  "Returns the roles in the frame."
  [frame]
  (body frame))

(defn role-pair-name
  "Returns the name of a role pair."
  [role-pair]
  (first role-pair))

(defn role-pair-filler
  "Returns the filler of a role pair."
  [role-pair]
  (first (rest role-pair)))

(defn role-filler
  "Returns the filler of the specified role in the frame."
  [role frame]
  (let [pair (filter #(= role (first %)) (body frame))]
    (if (nil? pair)
      pair
      (role-pair-filler (first pair)))))
