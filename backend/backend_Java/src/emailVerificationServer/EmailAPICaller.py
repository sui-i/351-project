import sys
import os
assert len(sys.argv)==4, "must include 3 system arguments: recipient's email, Subject and Message"
    
from email.message import EmailMessage
import ssl
import smtplib


def sendEmail(recipient :str, messageSubject :str, messageBody :str) -> int:
    '''
    Must return one of these codes.
    350: EmailSentSuccessfully
    351: EmailRecipientNotFound
    352: EmailServiceNotAvailable
    500: fatal internal error
    '''
    print(f"{recipient = } \n{messageSubject = } \n{messageBody = }")
    em = EmailMessage()
    em["From"] = MAIL
    em["To"] = recipient
    em["Subject"] = messageSubject
    em.set_content(messageBody)
    context = ssl.create_default_context()
    
    try:
        with smtplib.SMTP_SSL("smtp.gmail.com",465, context = context) as mail:
            mail.login(MAIL,PASSWORD)
            mail.sendmail(MAIL, recipient, em.as_string()) 
    except Exception as e:
        print(e, file=sys.stderr)
        if isinstance(e,smtplib.SMTPRecipientsRefused):
            return 351
        if isinstance(e,smtplib.SMTPHeloError) or isinstance(e,smtplib.SMTPAuthenticationError):
            return 352
        return 500
    return 350

MAIL = "hostelerhotel@gmail.com"
PASSWORD = "ymmyvojghdutaliw"

recipient = sys.argv[3]
messageSubject = sys.argv[1]
messageBody = sys.argv[2]


returnCode = sendEmail(recipient,messageSubject,messageBody)
print(returnCode)
sys.exit(returnCode) 
