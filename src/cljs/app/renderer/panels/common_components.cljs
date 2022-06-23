(ns app.renderer.panels.common-components
  (:require
   [re-frame.core :as re-frame]
   [app.renderer.events :as events]))

(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(defn import-xml-button
  []
  [:input
   {:type "file" :id "file" :name "file"
    :on-change
    #(re-frame/dispatch [::events/import-xml (-> % .-target .-files col->array js->clj)])}])
