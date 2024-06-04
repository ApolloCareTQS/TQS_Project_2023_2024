import React, { useContext, useState, useEffect } from 'react';
import { IonButtons, IonInput, IonItem, IonList, IonSelect, IonSelectOption } from '@ionic/react';
import './ScheduleFormContainer.css';
import { IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle } from '@ionic/react';
import axios from 'axios';
import { AuthContext } from '../App';

const ScheduleFormContainer: React.FC = () => {
    const [doctors, setDoctors] = useState([]);
    const [speciality, setSpeciality] = useState('CLINICA GERAL');
    const [date, setDate] = useState('');
    const [doctorId, setDoctorId] = useState('');
    const [clinicLocation, setClinicLocation] = useState('');
    const context = useContext(AuthContext);

    const specialties = ['CLINICA GERAL', 'CARDIOLOGIA', 'ORTOPEDIA']; // this should've come from backend

    useEffect(() => {
        getDoctors();
    }, []);

    const getDoctors = () => {
        axios.get('api/v1/doctors', { withCredentials: true })
            .then(response => {
                setDoctors(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const handleSchedule = async () => {
        const scheduledDate = new Date(date).getTime();

        const body = {
            id: null,
            scheduledDate: scheduledDate,
            checkInDate: null,
            receptionDate: null,
            duration: null,
            patientId: context.authState.id,
            doctorId: doctorId,
            state: "SCHEDULED",
            speciality: speciality, 
            location: clinicLocation,
        }

        await axios.post("api/v1/consultations/add", body, {withCredentials: true}).then(response => {
            window.location.href = '/'
            console.log(response);
        });
    }

    return (
        <IonCard>
            <IonCardHeader>
                <IonCardTitle color="secondary">Schedule a new appointment</IonCardTitle>
            </IonCardHeader>

            <IonCardContent>
                <IonList>
                    <IonItem>
                        <IonSelect value={speciality} placeholder="Select Speciality" onIonChange={e => setSpeciality(e.detail.value)}>
                            {specialties.map((spec, index) => (
                                <IonSelectOption key={index} value={spec}>{spec}</IonSelectOption>
                            ))}
                        </IonSelect>
                    </IonItem>
                    <IonItem>
                        <IonInput 
                            labelPlacement="stacked" 
                            type="date" 
                            label="Consultation Date"
                            value={date}
                            onIonChange={e => setDate(e.detail.value)}
                        ></IonInput>
                    </IonItem>
                    <IonItem>
                        <IonSelect value={doctorId} placeholder="Select Doctor" onIonChange={e => setDoctorId(e.detail.value)}>
                            {doctors.map((doctor, index) => (
                                <IonSelectOption key={index} value={doctor.id}>{doctor.name}</IonSelectOption>
                            ))}
                        </IonSelect>
                    </IonItem>
                    <IonItem>
                        <IonInput 
                            labelPlacement="stacked" 
                            type="text" 
                            label="Hospital of preference"
                            value={clinicLocation}
                            onIonChange={e => setClinicLocation(e.detail.value)}
                        ></IonInput>
                    </IonItem>
                </IonList>
                <IonButton onClick={handleSchedule} fill="outline">Submit</IonButton>
            </IonCardContent>
        </IonCard>
    );
}
export default ScheduleFormContainer;
