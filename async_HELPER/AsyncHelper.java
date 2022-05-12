class AsyncHelper extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        public AsyncHelper() {
            
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
		dialog = new ProgressDialog(ArtherCrop.this);
		dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
