./gradlew assembleDebug

curl -F "file=@app/build/outputs/apk/app-debug.apk" -F "token=${DEPLOYGATE_API_KEY}" -F "message=Test" https://deploygate.com/api/users/ChuuMong/apps