(ns app.main.core
  (:require
   [app.main.db :refer [save-db load-db]]
   ["electron" :refer [app BrowserWindow ipcMain]]))

(def main-window (atom nil))

(defn trigger-reload-db-event []
  (.send (.-webContents @main-window) "load-db" (load-db)))

(defn init-browser []
  (reset! main-window (BrowserWindow.
                       (clj->js {:width 800
                                 :height 600
                                 :webPreferences {:nodeIntegration true
                                                  :contextIsolation false}})))
  ; Path is relative to the compiled js file (main.js in our case)
  (.loadURL ^js/electron.BrowserWindow @main-window
            (str "file://" js/__dirname "/public/index.html"))
  (.on @main-window "show" trigger-reload-db-event)
  (.on ^js/electron.BrowserWindow @main-window "closed" #(reset! main-window nil))
  )

(defn main []

  (.on ipcMain "save-db" save-db)
  (.on app "window-all-closed" #(when-not (= js/process.platform "darwin")
                                  (.quit app)))
  (.on app "ready" init-browser))
