import React from 'react';
import { IonButtons, IonInput, IonItem, IonList } from '@ionic/react';
import './ScheduleFormContainer.css';
import { IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle } from '@ionic/react';

function ScheduleFormContainer() {
  return (
        <IonCard>
            <IonCardHeader>
                <IonCardTitle color="secondary">Schedule a new appointment</IonCardTitle>
            </IonCardHeader>

            <IonCardContent>
                <IonList>
                    <IonItem>
                        <IonInput labelPlacement="stacked" label="Name"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" label="Email" placeholder="example@gmail.com"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" label="Speciality" placeholder="Dermatology"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" type="date" label="Date of birth"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" label="Reason for visit"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" type="text" label="Doctor"></IonInput>
                    </IonItem>

                    <IonItem>
                        <IonInput labelPlacement="stacked" type="text" label="Hospital of preference"></IonInput>
                    </IonItem>
                </IonList>
                <IonButton fill="outline">Submit</IonButton>
            </IonCardContent>
        </IonCard>
  );
}
export default ScheduleFormContainer;