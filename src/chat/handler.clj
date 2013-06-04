(ns chat.handler
  (:use chat.routes.auth
        chat.routes.home
        compojure.core)
  (:require [noir.util.middleware :as middleware]
            [noir.session :as session]
            [compojure.route :as route]
            [chat.models.schema :as schema]
            [taoensso.timbre :as timbre]
            [com.postspectacular.rotor :as rotor]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "runs when the application starts and checks if the database
   schema exists, calls schema/create-tables if not."
  []
  (timbre/set-config!
    [:appenders :rotor]
    {:min-level :info
     :enabled? true
     :async? false ; should be always false for rotor
     :max-message-per-msecs nil
     :fn rotor/append})
  
  (timbre/set-config!
    [:shared-appender-config :rotor]
    {:path "chat.log" :max-size (* 512 1024) :backlog 10})
  
  (if-not (schema/initialized?)
    (schema/create-tables))
  
  (timbre/info "chat started successfully"))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  [] 
  (timbre/info "chat is shutting down..."))

;;append your application routes to the all-routes vector
(def all-routes [auth-routes home-routes app-routes])

(def app (middleware/app-handler all-routes 
                                 ;;put any custom middleware
                                 ;;in the middleware vector
                                 :middleware []
                                 ;;add access rules here
                                 :access-rules []))

(def war-handler (middleware/war-handler app))
