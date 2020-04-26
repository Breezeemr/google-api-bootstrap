(ns com.breezeehr.google-api.boostrap
  (:require [aleph.http :as http]
            [cemerick.url :as url]))


(def lastmessage (atom nil))

(defn list-apis
  ([client]
   (list-apis client {"fields" "items.discoveryRestUrl,items.name,items.version"}))
  ([client options]
   (->
     {:method       :get
      :url          (-> (url/url "https://www.googleapis.com")
                        (assoc :path "/discovery/v1/apis")
                        str)
      :query-params options}
     (assoc :throw-exceptions false
            ;:aleph/save-request-message lastmessage
            :as :json)
     http/request
     deref
     :body
     :items)))

(defn get-discovery [client {:keys [discoveryRestUrl] :as discovery-ref}]
  (-> {:method :get
       :url    discoveryRestUrl}
      ;(add-auth client)
      (assoc :throw-exceptions false
             ;:aleph/save-request-message lastmessage
             :as :json)
      http/request
      deref
      :body))





(comment
  (def client {})
  ;auth is not required to discovery
  (list-apis client)
  (get-discovery
    client
    {:name "discovery",
     :version "v1",
     :discoveryRestUrl "https://www.googleapis.com/discovery/v1/apis/discovery/v1/rest"}))
