import streamlit as st 
import pdfplumber 
from sentence_transformers import SentenceTransformer 
from sklearn.metrics.pairwise import cosine_similarity 
import numpy as np 
from langchain_text_spli ers import RecursiveCharacterTextSpli er 
 
# Page configura on 
st.set_page_config(page_ tle=" NoteBot", layout="centered") 
st. tle(" NoteBot") 
 
with st.sidebar: 
    st. tle(" My Notes") 
    file = st.file_uploader(" Upload notes PDF and start asking ques ons", type="pdf") 
 
# Load embedding model 
embed_model = SentenceTransformer('all-MiniLM-L6-v2') 
 
if file is not None: 
    with st.spinner(" Extrac ng and processing your PDF..."): 
        # Extract text 
        text = "" 
        try: 
            with pdfplumber.open(file) as pdf: 
                for page in pdf.pages: 
                    extracted_text = page.extract_text() 
                    if extracted_text: 
                        text += extracted_text + "\n" 
        except Excep on as e: 
            st.error(f" Error while reading PDF: {e}") 
            st.stop() 
 
        if not text.strip(): 
            st.warning(" No extractable text found in the PDF.") 
            st.stop() 
 
        # Split text into chunks 
        spli er = RecursiveCharacterTextSpli er(chunk_size=250, chunk_overlap=50) 
        chunks = spli er.split_text(text) 
        if not chunks: 
            st.warning(" Failed to split text into chunks.") 
            st.stop() 
 
        # Embed chunks 
        try: 
            chunk_embeddings = embed_model.encode(chunks, normalize_embeddings=True) 
        except Excep on as e: 
            st.error(f" Error during embedding genera on: {e}") 
            st.stop() 
 
    # Query input 
    user_query = st.text_input(" Type your ques on here") 
 
    if user_query: 
        with st.spinner(" Thinking..."): 
            try: 
                query_embedding = embed_model.encode([user_query], normalize_embeddings=True) 
                similari es = cosine_similarity([query_embedding[0]], chunk_embeddings)[0] 
                top_k = 3 
                top_indices = similari es.argsort()[-top_k:][::-1] 
                top_scores = similari es[top_indices] 
                matching_chunks = [chunks[i] for i in top_indices] 
            except Excep on as e: 
                st.error(f" Error during query search: {e}") 
                st.stop() 
 
        # Threshold-based detec on 
        if matching_chunks: 
            top_score = top_scores[0]  # Cosine similarity of best match 
            threshold = 0.65  # Tune this between 0.6–0.75 
 
            if top_score < threshold: 
                st.subheader(" Out-of-Syllabus Alert") 
                st.warning(" I don't know. Your ques on seems outside the uploaded notes.") 
            else: 
                st.subheader(" Answer based on your notes:") 
                for i, chunk in enumerate(matching_chunks, 1): 
                    with st.expander(f" View Chunk {i}"): 
                        st.write(chunk) 
        else: 
            st.info(" No relevant informa on found.") 
 