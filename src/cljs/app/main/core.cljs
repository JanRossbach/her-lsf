(ns app.main.core
  (:require ["electron" :refer [app BrowserWindow crashReporter]]
            ["fs" :as fs]))

(defn write-hello []
  (.writeFileSync fs "./hello" "Hello World"))

(def main-window (atom nil))

(defn init-browser []
  (reset! main-window (BrowserWindow.
                        (clj->js {:width 800
                                  :height 600
                                  :webPreferences
                                  {:nodeIntegration true}})))
  ; Path is relative to the compiled js file (main.js in our case)
  (.loadURL ^js/electron.BrowserWindow @main-window (str "file://" js/__dirname "/public/index.html"))
  (.on ^js/electron.BrowserWindow @main-window "closed" #(reset! main-window nil)))

(defn main []
  ; CrashReporter can just be omitted
  ;; (.start crashReporter
  ;;         (clj->js
  ;;          {:companyName "HHU"
  ;;           :productName "HER-LSF"
  ;;           :submitURL "https://example.com/submit-url"
  ;;           :autoSubmit false}))
  (write-hello)

  (.on app "window-all-closed" #(when-not (= js/process.platform "darwin")
                                  (.quit app)))
  (.on app "ready" init-browser))
