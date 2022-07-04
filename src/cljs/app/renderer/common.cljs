(ns app.renderer.common
  (:require
   [re-com.core :as re-com]
   [re-frame.core :as re-frame]))

;; App Constants

(def gap-size "10px")
(def big-gap-size "50px")
(def box-class "container")
(def default-search-term #".*.*")

(defn make-search-regex
  "Helper Function to convert a search-string that was put into
  the search field into the regex that is stored in the app db and then
  used to query the ds db."
  [search-string]
  (re-pattern (str ".*" search-string ".*")))

(defn search-field
  []
  [re-com/h-box
   :gap gap-size
   :class box-class
   :children [[re-com/input-text
               :model ""
               :on-change #(re-frame/dispatch [:app.renderer.events/set-search-term (make-search-regex %)])
               :placeholder "Search"
               :change-on-blur? true]
              [re-com/button
               :label "Reset Search"
               :on-click #(re-frame/dispatch [:app.renderer.events/set-search-term default-search-term])]]])

(defn back-button
  [target]
  [re-com/button
   :label "Back"
   :class "btn-info"
   :on-click #(re-frame/dispatch [:app.renderer.events/navigate target])])
