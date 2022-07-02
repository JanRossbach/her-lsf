(ns app.renderer.panels.common-components
  (:require
   [re-com.core :as re-com]
   [re-frame.core :as re-frame]
   [app.renderer.events :as events]))


(defn make-search-regex
  "Helper Function to convert a search-string that was put into
  the search field into the regex that is stored in the app db and then
  used to query the ds db."
  [search-string]
  (re-pattern (str ".*" search-string ".*")))

(defn search-field
  []
  [re-com/h-box
   :gap "10px"
   :class "container"
   :children [[re-com/input-text
               :model ""
               :on-change #(re-frame/dispatch [::events/set-search-term (make-search-regex %)])
               :placeholder "Search"
               :change-on-blur? true]
              [re-com/button
               :label "Reset Search"
               :on-click #(re-frame/dispatch [::events/set-search-term #".*.*"])]]])
