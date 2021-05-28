import firebase from 'firebase/app'
import "firebase/storage"

const firebaseConfig = {
    apiKey: "AIzaSyAM-qK2qOcIwb2YJtUucjzuKCF4nJzhq_A",
    authDomain: "envirifystorage.firebaseapp.com",
    projectId: "envirifystorage",
    storageBucket: "envirifystorage.appspot.com",
    messagingSenderId: "884369475697",
    appId: "1:884369475697:web:c8b2877dc141f42845b3d0",
    measurementId: "G-M6FJ4T3JV4"
  };

  firebase.initializeApp(firebaseConfig);

  const storage = firebase.storage()

  export {storage, firebase as default}