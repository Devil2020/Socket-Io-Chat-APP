const express = require('express'),
http = require('http'),
app = express(),
server = http.createServer(app),
io = require('socket.io').listen(server);
app.get('/', (req, res) => {

res.send('Chat Server is running on port 3000')
});
io.on('connection', (socket) => {
console.log('user connected')
socket.on('joinPerson', function(userNickname) {

        console.log(userNickname +" : has joined the chat "  );

        socket.broadcast.emit('SayHello',userNickname +" : has joined the chat , Say Welcome to him ");
    })
socket.on('MessageSend', (senderNickname,messageContent,imageBitmap) => {
       console.log(senderNickname+" : " +messageContent)
      let  message = {"message":messageContent, "senderNickname":senderNickname , "senderImage":imageBitmap}
      socket.broadcast.emit('MessageRecieve', message )
      })
socket.on('disconnect', function() {
        console.log(userNickname +' has left ')
        socket.broadcast.emit( "userdisconnect" , userNickname +' user has left')

    })
})
server.listen(3000,()=>{
console.log('Node app is running on port 3000')

})