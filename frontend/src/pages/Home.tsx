import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import styles from './Home.module.css';
import ScheduleFormContainer from '../components/ScheduleFormContainer';
import AgendaContainer from '../components/AgendaContainer';
import { calendar } from 'ionicons/icons';

// Import Swiper React components
import { Swiper, SwiperSlide } from 'swiper/react';
import { register } from 'swiper/element/bundle';

// Import Swiper styles
import 'swiper/css';

register();

const Home: React.FC = () => {

  function goToSchedule() {

  }

  return (
    <>
      <IonMenu side='end' contentId='main-content'>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Menu</IonTitle>
          </IonToolbar>
        </IonHeader>
      </IonMenu>
      <IonPage style={styles} id='main-content'>
        <IonHeader>
          <IonToolbar>
            <IonTitle color='primary'>ApolloCare</IonTitle>
            <IonButtons slot="end">
              <IonButton href='/schedule' color='success' fill='outline'>Schedule&nbsp;<IonIcon icon={calendar}></IonIcon></IonButton>
              <IonMenuButton></IonMenuButton>
            </IonButtons>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <main>
            <swiper-container navigation={true}>
              <swiper-slide>
                <span>
                My ApolloCare: Your personal online area
                <br />
                <a href='#'>Learn more</a>
                </span>
              </swiper-slide>
              <swiper-slide>We have a variaty of different services for you</swiper-slide>
            </swiper-container>
          </main>
          <IonGrid>
            <IonRow>
              <IonCol>
                <div className={styles.services_div}>
                  <br />
                  <IonLabel className={styles.services_title} color='primary'>Services</IonLabel>
                  <br />
                  <IonLabel color='secondary'>
                    <br />
                    We bet on the high quality of our services and on the satisfaction of our users, prescribers, and partners.
                    <br />
                    <br />
                    Discover the services we have at your disposal.
                    <br />
                    <br />
                  </IonLabel>
                </div>
              </IonCol>
            </IonRow>
            <IonRow>
              <IonCol>
                <IonCard>
                  <IonCardContent>
                    <div className={styles.services_card}>
                      <IonLabel>Check-Ups</IonLabel>
                    </div>
                  </IonCardContent>
                </IonCard>
              </IonCol>
            </IonRow>
            <IonRow>
              <IonCol>
                <IonCard>
                  <IonCardContent>
                    <div className={styles.services_card}>
                      <IonLabel>Medical Exams</IonLabel>
                    </div>
                  </IonCardContent>
                </IonCard>
              </IonCol>
            </IonRow>
            <IonRow>
              <IonCol>
                <IonCard>
                  <IonCardContent>
                    <div className={styles.services_card}>
                      <IonLabel>Clinical Areas</IonLabel>
                    </div>
                  </IonCardContent>
                </IonCard>
              </IonCol>
            </IonRow>
            <IonRow>
              <IonCol>
                <div className={styles.explore_all}>
                  <a href='#'>Explore all services</a>
                </div>
              </IonCol>
            </IonRow>
          </IonGrid>
        </IonContent>
      </IonPage>
    </>
  );
};

export default Home;
