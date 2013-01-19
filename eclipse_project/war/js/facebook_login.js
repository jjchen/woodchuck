/* 
 * facebook_login.js
 *
 * additional javascript functions for the facebook login stuff
 */

// Additional JS functions here
window.fbAsyncInit = function() {
    FB.init({
        appId      : '457817960938308', // App ID
        channelUrl : '//www.wc.ptzlabs.com/channel.html', // Channel File
        status     : true, // check login status
        cookie     : true, // enable cookies to allow server to access session
        xfbml      : true  // parse XFBML
    });

    // Additional init code here -- specifically get login status
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            // connected
            
            /** NOTE: facebook response object will contain this information
              {
                status: 'connected',
                authResponse: {
                    accessToken: '...',
                    expiresIn:'...',
                    signedRequest:'...',
                    userID:'...'
                }
              }
             ******/

            $.ajax({
                type: "POST",
                url: "/login",
                data: {
                    userid: response.authResponse.userID,
                    access_token: response.authResponse.accessToken
                }
            }).done(function (msg) {
                console.log("hello" + msg);
            });
            
            var fbid = response.authResponse.userID;
  
        } else if (response.status === 'not_authorized') {
            // not_authorized
            login();
        } else {
            // not_logged_in
            login();
        }
    });

};

// Load the SDK Asynchronously
(function(d){
    var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement('script'); js.id = id; js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    ref.parentNode.insertBefore(js, ref);
}(document));

function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            // connected
        } else {
            // cancelled
        }
    }, {scope: 'email, publish_stream'});
}
