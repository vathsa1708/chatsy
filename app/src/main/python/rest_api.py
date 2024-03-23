from better_profanity import profanity

def rest(string):
    return profanity.censor(string)
    
# print(rest('fuck you'))