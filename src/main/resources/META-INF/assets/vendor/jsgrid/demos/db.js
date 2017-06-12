(function() {

    var db = {

        loadData: function(filter) {
            return $.grep(this.clients, function(client) {
                return (!filter.Users || client.Users.indexOf(filter.Users) > -1)
                    && (!filter.Login || client.Login.indexOf(filter.Login) > -1)
                    && (!filter.Profil || client.Profil === filter.Profil)
                
            });
        },

        insertItem: function(insertingClient) {
            this.clients.push(insertingClient);
        },

        updateItem: function(updatingClient) { },

        deleteItem: function(deletingClient) {
            var clientIndex = $.inArray(deletingClient, this.clients);
            this.clients.splice(clientIndex, 1);
        }

    };

    window.db = db;


    db.countries = [
        { Name: "", Id: 0 },
        { Name: "PPE", Id: 1 },
        { Name: "Service achats", Id: 2 },
        { Name: "Développeur", Id: 3 },
        
    ];

    db.clients = [
         {
            "Users": "Abdelkrim Akkeri",
            "Profil": 3,
            "Login": "AKAB1001",
			            
        },
		{
            "Users": "Ibtihel El Bache",
            "Profil": 1,
            "Login": "ELIB1001",
            
        },
        
        {
            "Users": "Radhouene Ben Haj Bel Kacem",
            "Profil": 2,
            "Login": "BERA1001",
            
        },
        
        
        {
            "Users": "Akram Anaya",
            "Profil": 3,
            "Login": "ANAK1001",
            
        },
        
       
       
       
    ];

    db.Users = [
        {
            "ID": "x",
           // "Account": "A758A693-0302-03D1-AE53-EEFE22855556",
            "Users": "Carson Kelley",
           // "RegisterDate": "2002-04-20T22:55:52-07:00"
        },
        {
           // "Account": "D89FF524-1233-0CE7-C9E1-56EFF017A321",
            "Users": "Prescott Griffin",
           // "RegisterDate": "2011-02-22T05:59:55-08:00"
        },
                      
        
        {
           // "Account": "00E46809-A595-CE82-C5B4-D1CAEB7E3E58",
            "Users": "Philip Galloway",
            //"RegisterDate": "2008-08-21T18:59:38-07:00"
        },
        
     ];

}());