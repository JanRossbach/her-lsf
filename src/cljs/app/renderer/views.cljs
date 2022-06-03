(ns app.renderer.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [:h1
     :label (str "Hello from " @name)
     :level :level1]))

(defn root-component []
  [:div
   [title]
   [re-com/title :label "Hello"]
   [:div.logos
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:input {:type "file" :id "file" :name "file"
            :on-change #(re-frame/dispatch [::events/add-hello])}]])
