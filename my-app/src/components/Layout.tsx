import Head from 'next/head';

// import AuthenticationInfo from './AuthenticationInfo';
import Footer from '@/components/Footer';
import Header from '@/components/Header';

type Props = {
  children: React.ReactNode;
};

export default function Layout({ children }: Props) {
  return (
    <>
      {/* <Head>
        <title>Yas - Storefront</title>
        <meta name="description" content="Yet another shop storefront" />
        <link rel="icon" href="/favicon.ico" />
      </Head> */}
      {/* <Header>
        <AuthenticationInfo />
      </Header> */}
      <Header />
      <div className="body">{children}</div>
      <Footer />
      <script
        dangerouslySetInnerHTML={{
          __html: `
  document.addEventListener('DOMContentLoaded', function() {
    const menus = document.querySelectorAll('.navbar-burger');
    const dropdowns = document.querySelectorAll('.navbar-menu');

    if (menus.length && dropdowns.length) {
        for (var i = 0; i < menus.length; i++) {
            menus[i].addEventListener('click', function() {
                for (var j = 0; j < dropdowns.length; j++) {
                    dropdowns[j].classList.toggle('hidden');
                }
            });
        }
    }
});
`,
        }}
      />
    </>
  );
}
