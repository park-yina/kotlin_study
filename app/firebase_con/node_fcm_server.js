const admin=require('firebase-admin')
var serviceAccount=require('./server_key.json')
let serviceAccount=require("./firebase-admin.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
})
const admin=require()
var token=;
var fcm_message = {
    notification: {
        title:'noti title',
        body: 'noti body..'
    },
    data: {
        title:'data title',
        value: '20'
    },
    token: token
}

admin.messaging().send(fcm_message)
    .then(function(response){
        console.log('send ok...')
    })
    .catch(function(error){
        console.log('send error...')
    })