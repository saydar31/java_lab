db.users.insert({
   name: "Aydar",
   rating: 10,
   city: "Kazan"
});
db.users.insert({
   name: "Foo",
   rating: 10,
   city: "Bar"
});
db.courses.insert({
   name:"Algebra",
   rating: 10,
   tags:["math","algebra","matrix"]
});

db.courses.insert({
   name: "BD",
   rating: 10,
   tags:["math","algebra","matrix"],
   teacher:{
       name:"Teacher",
       lastName: "Teacher",
       employmentYear:1991
   }
});

db.courses.insert({
   name: "ML",
   rating: 11,
   tags:["it","algebra","matrix"],
   teacher:{
       name:"Teacher",
       lastName: "Teacher",
       employmentYear:2005
   }
});

db.courses.find({tags:"math"});

db.users.find({name:"Aydar"})
