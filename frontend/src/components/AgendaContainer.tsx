import React from 'react';
import { IonButtons, IonCol, IonGrid, IonInput, IonItem, IonLabel, IonList, IonRow } from '@ionic/react';
import { IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle } from '@ionic/react';

import styles from './AgendaContainer.module.css';

function AgendaContainer() {

    const agendaData = [
        {
            speciality: 'Cardiology',
            date: '2024-05-05',
            hour: '10:00 AM',
            doctor: 'Dr. Smith',
            hospital: 'General Hospital'
        },
        {
            speciality: 'Dermatology',
            date: '2024-05-06',
            hour: '02:30 PM',
            doctor: 'Dr. Johnson',
            hospital: 'City Clinic'
        },
        {
            speciality: 'Dermatology',
            date: '2024-05-06',
            hour: '02:30 PM',
            doctor: 'Dr. Johnson',
            hospital: 'City Clinic'
        },
        {
            speciality: 'Dermatology',
            date: '2024-05-06',
            hour: '02:30 PM',
            doctor: 'Dr. Johnson',
            hospital: 'City Clinic'
        },
        {
            speciality: 'Dermatology',
            date: '2024-05-06',
            hour: '02:30 PM',
            doctor: 'Dr. Johnson',
            hospital: 'City Clinic'
        },
    ];

    return (
        <IonCard>
            <IonCardHeader>
                <IonCardTitle>Agenda</IonCardTitle>
            </IonCardHeader>

            <IonCardContent>
                <IonGrid>
                    <IonRow className={styles.header}>
                        <IonCol className={styles.col}>
                            <IonLabel>
                                <h1>
                                    Speciality
                                </h1>
                            </IonLabel>
                        </IonCol>
                        <IonCol className={styles.col}>
                            <IonLabel>
                                <h1>
                                    Date 
                                </h1>
                            </IonLabel>
                        </IonCol>
                        <IonCol className={styles.col}>
                            <IonLabel>
                                <h1>
                                    Hour 
                                </h1>
                            </IonLabel>
                        </IonCol>
                        <IonCol className={styles.col}>
                            <IonLabel>
                                <h1>
                                    Doctor 
                                </h1>
                            </IonLabel>
                        </IonCol>
                        <IonCol className={styles.col}>
                            <IonLabel>
                                <h1>
                                    Hospital 
                                </h1>
                            </IonLabel>
                        </IonCol>
                    </IonRow>

                    {/* Map over the agendaData array to render each item */}
                    {agendaData.map((item, index) => (
                        <IonRow key={index} className={styles.row}>
                            <IonCol className={styles.col}>
                                <IonLabel>{item.speciality}</IonLabel>
                            </IonCol>
                            <IonCol className={styles.col}>
                                <IonLabel>{item.date}</IonLabel>
                            </IonCol>
                            <IonCol className={styles.col}>
                                <IonLabel>{item.hour}</IonLabel>
                            </IonCol>
                            <IonCol className={styles.col}>
                                <IonLabel>{item.doctor}</IonLabel>
                            </IonCol>
                            <IonCol className={styles.col}>
                                <IonLabel>{item.hospital}</IonLabel>
                            </IonCol>
                        </IonRow>
                    ))}
                </IonGrid>
            </IonCardContent>
        </IonCard>
    );
}

export default AgendaContainer;
