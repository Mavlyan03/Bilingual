INSERT INTO users(id, email, password, role)
VALUES (1, 'admin@gmail.com', '$2a$12$LqafUxGS/5uLAAZdt3e6y.JMg9jtqwqD6oFFuXKlQZ/HbcCS41F0.',
        'ADMIN'),
       (2, 'mavlyansadirov03@gmail.com', '$2a$12$knJX9iuGC6bpp8f5CxEJb.nXbAJYna0x4S262JKS2Huky9ERRZCFi',
        'CLIENT'),
       (3, 'client@gmail.com', '$2a$12$KsswQBeQJb0XLmmwpwrOt.39W4Whw/ufZoQSZEsSi2lBG1ndSX2We',
        'CLIENT');

INSERT INTO clients(id, first_name, last_name, user_id)
VALUES (1, 'Mavlyan', 'Sadirov', 2),
       (2, 'John', 'Doe', 3);

INSERT INTO tests(id, description, is_active, title)
VALUES (1, 'You should pass the test on english', true, 'Test #1'),
       (2, 'This test is good for your english', false, 'English Test');

INSERT INTO contents(id, content, content_format)
VALUES (1, 'audio', 'AUDIO'),
       (2, 'text', 'TEXT'),
       (3, 'image', 'IMAGE'),
       (4, 'record', 'RECORD');

INSERT INTO questions (id, correct_answer, duration, is_active, min_words, number_of_replays, option_type,
                       passage, question_number, question_type, statement, title, content_id, test_id)
VALUES (1, null, 1, true, null, null, 'MULTIPLE', null, 1, 'SELECT_THE_REAL_ENGLISH_WORDS', null,
        'Select real english word', 3, 1),
       (2, null, 1, true, null, null, 'MULTIPLE', null, 2, 'LISTEN_AND_SELECT_ENGLISH_WORDS', null,
        'Select real english word', 1, 1),
       (3, 'Hello, how is it going?', 1, true, null, 2, null, null, 3, 'TYPE_WHAT_YOU_HEAR', null,
        'Write what are listening', 1, 1),
       (4, 'Protecting nature means protecting the Motherland', 1, true, null, null, null, null, 4, 'DESCRIBE_IMAGE',
        null, 'Write one more sentences that describe the images', 2, 1),
       (5, 'mai ankyl iz ət wɜːk', 1, true, null, null, null, null, 5, 'RECORD_SAYING_STATEMENT',
        'poka z ne znau shto write', 'Record yourself saying the statement', 1, 1),
       (6, null, 1, true, 50, null, null, null, 6, 'RESPOND_IN_AT_LEAST_N_WORDS',
        'Describe a time you were surprised? What happened?', 'Respond to the question at least 30 word', 3, 1),
       (7,
        'Meta Platforms is an American multinational holding company that owns a technology conglomerate and is located in Menlo Park, California.',
        1, true, null, null, null,
        'Meta Platforms is an American multinational holding company that owns a technology conglomerate and is located in Menlo Park, California. Facebook Instagram, WhatsApp and Oculus are the parent organization. Facebook Facebook is one of the most expensive companies in the world, and is also considered one of the "big five" companies in the field of information technology in the USA, along with Amazon, Alphabet (owned by Google), Meta offers other products and services, including Facebook Messenger, Facebook Watch and Facebook Portal, the company also acquired Giphy and Mapillary, has 9.99 % of shares in Jio Platforms.',
        7, 'HIGHLIGHT_THE_ANSWER', 'What is Meta and where is located?', 'Highlight the answer to the question below',
        3, 1),
       (8, null, 1, true, null, null, 'SINGLETON',
        'We study one of the modern languages at school. It is English. It is my favourite subject. At the lessons of English we learn to read, write and speak. We learn the History and Geography of bur country, Great Britain and the USA. We read stories after famous English and American children is writers. I like stories after Alan Milne, Donald Bisset, Lewis Carroll, Mark Twain and others. I want to be clever at English because English will help me in my future life. I shall read books in English, watch films and listen to songs and understand them. But what is more important, I shall speak with people from other countries and we will understand each other. We will make friends and will live in peace.',
        8, 'SELECT_THE_MAIN_IDEA', null, 'Select the main idea that is expressed in the passage', 3, 1),
       (9, null, 1, true, null, null, 'SINGLETON',
        'A programming language is a system of notation for writing computer programs. Most programming languages are text-based formal languages, but they may also be graphical. They are a kind of computer language. The description of a programming language is usually split into the two components of syntax (form) and semantics (meaning), which are usually defined by a formal language. Some languages are defined by a specification document (for example, the C programming language is specified by an ISO Standard) while other languages (such as Perl) have a dominant implementation that is treated as a reference. Some languages have both, with the basic language defined by a standard and extensions taken from the dominant implementation being common. Programming language theory is a subfield of computer science that deals with the design, implementation, analysis, characterization, and classification of programming languages.',
        9, 'SELECT_THE_BEST_TITLE', null, 'Select the best title for the passage', 3, 1);

INSERT INTO options (id, is_true, title, option, question_id)
VALUES (1, true, 'string', null, 1),
       (2, false, 'intejer', null, 1),
       (3, true, 'boolean', null, 1),
       (4, false, 'cshar', null, 1),

       (5, true, 'word1', 'https://bilingual.s3.eu-central-1.amazonaws.com/1670280385813Запись.m4a', 3),
       (6, true, 'word2', 'https://bilingual.s3.eu-central-1.amazonaws.com/1670280385813Запись.m4a', 3),
       (7, false, 'word3', 'https://bilingual.s3.eu-central-1.amazonaws.com/1670280385813Запись.m4a', 3),
       (8, false, 'word4', 'https://bilingual.s3.eu-central-1.amazonaws.com/1670280385813Запись.m4a', 3),

       (9, true,
        'This text is written about the English subject and shows that English is needed and relevant everywhere',
        null, 2),
       (10, false,
        'Drawing is an interesting subject, too. I''m fond of drawing and painting. When we draw, we make pictures with a pen or chalk.',
        null, 2),
       (11, false, 'I do not understand this text!!!!', null, 2),

       (12, false, 'English language', null, 2),
       (13, false, 'My family', null, 2),
       (14, true, 'Programming language', null, 2);

insert into results (id, date_of_submission, score, status, client_id, test_id)
VALUES (1, '2022-12-10 20:04:44.794364', 43, 'NOT_EVALUATED', 1, 1);

insert into question_answers (id, number_of_words, score, text_response_user, content_id, question_id, result_id, seen,
                              status, count_of_plays)
values (1, null, 5, null, 3, 1, 1, false, 'NOT_EVALUATED', null),
       (2, null, 5, null, 1, 2, 1, false, 'NOT_EVALUATED', null),
       (3, null, 5, 'I heard beautifully nature', 1, 3, 1, false, 'NOT_EVALUATED', 2),
       (4, null, 7, 'I heard beautifully nature', 2, 4, 1, false, 'NOT_EVALUATED', null),
       (5, null, 4, 'record1.mp3', 1, 5, 1, false, 'NOT_EVALUATED', null),
       (6, 14, 9,
        'As the most popular programming language for over 20 years, Java has a whole host of backend frameworks, but their reliability and versatility vary widely. Today’s most popular Java backend frameworks are:',
        3, 6, 1, false, 'NOT_EVALUATED', null),
       (7, null, 3, 'As the most popular programming language for over 20 years', 3, 7, 1, false, 'NOT_EVALUATED',
        null),
       (8, null, 10, null, 3, 8, 1, false, 'NOT_EVALUATED', null),
       (9, null, 0, null, 3, 9, 1, false, 'NOT_EVALUATED', null);

INSERT INTO question_answers_options (question_answer_id, options_id)
VALUES (1, 1),
       (1, 2),
       (2, 5),
       (2, 7),
       (8, 9),
       (9, 14);