db = db.getSiblingDB('notes');
db.createUser({
  user: 'app_user',
  pwd: 'app_pass1234',
  roles: [{ role: 'readWrite', db: 'notes' }]
});