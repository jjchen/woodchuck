filepicker.setKey('ArDRHUpYoQQ6Yg5Qsmoxbz');

filepicker.pick({
    mimetypes: ['application/pdf', 'text/plain'],
    container: 'window',
    services:['COMPUTER', 'DROPBOX', 'GOOGLE_DRIVE'],
  },
  function(FPFile){
    // send FPFile to database
    console.log(FPFile.url);
    console.log(FPFile.filename);
    console.log(FPFile.mimetype);
  },
  function(FPError){
    console.log(FPError.toString());
  }
);

//var url = read from database;
//var filename = read from database;

//console.log("Loading "+filename);
//filepicker.read(url, function(data){
//	console.log(data);	
//});

