import speech_recognition as sr

r = sr.Recognizer()

with sr.Microphone() as source:
    print("Speak now...")
    audio = r.listen(source)

try:
    command = r.recognize_google(audio)
    print(command)
except sr.UnknownValueError:
    print("")  # Nothing recognized
except sr.RequestError:
    print("Error with Google API")